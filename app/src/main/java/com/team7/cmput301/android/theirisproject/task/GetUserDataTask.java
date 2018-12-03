/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.task;

import android.os.AsyncTask;

import com.team7.cmput301.android.theirisproject.IrisProjectApplication;
import com.team7.cmput301.android.theirisproject.model.BodyPhoto;
import com.team7.cmput301.android.theirisproject.model.CareProvider;
import com.team7.cmput301.android.theirisproject.model.Comment;
import com.team7.cmput301.android.theirisproject.model.Patient;
import com.team7.cmput301.android.theirisproject.model.Problem;
import com.team7.cmput301.android.theirisproject.model.ProblemList;
import com.team7.cmput301.android.theirisproject.model.Record;
import com.team7.cmput301.android.theirisproject.model.RecordList;
import com.team7.cmput301.android.theirisproject.model.RecordPhoto;
import com.team7.cmput301.android.theirisproject.model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.params.Parameters;

/**
 * Asynchronously gets all of a User's data.
 * Uses nested calls to achieve this (e.g. getting one Patient will get all its Problems,
 * which will get all those Problems' Records, etc)
 *
 * @author anticobalt
 */
public class GetUserDataTask extends AsyncTask<User, Void, Void> {

    private Callback cb;

    private final String MATCH = "match";
    private final String TERM = "term";

    private final String USER = "user";
    private final String PROBLEM = "problem";
    private final String RECORD = "record";
    private final String BODYPHOTO = "bodyphoto";
    private final String RECORDPHOTO = "recordphoto";
    private final String COMMENT = "comment";


    public GetUserDataTask(Callback cb) {
        this.cb = cb;
    }

    @Override
    protected Void doInBackground(User... users) {

        User user = users[0];
        IrisProjectApplication.addUserToCache(user);

        switch (user.getType()) {
            case PATIENT:
                getAndBindProblems((Patient) user);
                getAndBindBodyPhotos((Patient) user);
                getAndBindCareProviders((Patient) user);
                break;
            case CARE_PROVIDER:
                getAndBindPatients((CareProvider) user);
                break;
            default:
                System.err.println(String.format("Unhandled user type in %s.",
                        this.getClass().getSimpleName()));
                break;
        }

        return null;

    }

    /**
     * Make and attach all Patients (and their proper data) with a specified Care Provider to its Patient list
     *
     * @param careProvider the bound-to Care Provider
     */
    private void getAndBindPatients(CareProvider careProvider) {
        List<Patient> patients = new ArrayList<>();

        for (String patientId : careProvider.getPatientIds()) {
            String query = generateQuery(TERM, "_id", patientId);
            SearchResult res = search(query, "user");

            if (res == null) {
                printError(CareProvider.class, careProvider.getId());
            }

            Patient patient = res.getSourceAsObject(Patient.class, true);
            patients.add(patient);
        }
        careProvider.setPatients(patients);

        for (Patient patient: patients) {
            IrisProjectApplication.addUserToCache(patient);
            getAndBindProblems(patient);
            getAndBindBodyPhotos(patient);
        }

    }

    /**
     * Make and attach Care Providers of a Patient.
     * Patients only need to know Care Providers contact info,
     * so binding all of their Care Provider's other Patients is not required.
     *
     * @param patient the bound-to Patient
     */
    private void getAndBindCareProviders(Patient patient) {

        String query = generateQuery(MATCH, "patientIds", patient.getId());
        SearchResult res = search(query, USER);

        if (res != null) {

            List<CareProvider> careProviders = res.getSourceAsObjectList(CareProvider.class, true);
            patient.setCareProviders(careProviders);

        } else printError(Patient.class, patient.getId());

    }

    /**
     * Make and attach all Problems (and their aggregate model data) to a specified Patient
     *
     * @param patient the bound-to Patient
     */
    public void getAndBindProblems(Patient patient) {

        String query = generateQuery(TERM, "user", patient.getId());
        SearchResult res = search(query, PROBLEM);

        if (res != null) {

            ProblemList problems = new ProblemList(res.getSourceAsObjectList(Problem.class, true));
            patient.setProblems(problems);

            for (Problem problem : problems) {
                IrisProjectApplication.addProblemToCache(problem);
                getAndBindComments(problem);
                getAndBindRecords(problem);
            }

        } else printError(Patient.class, patient.getId());

    }

    public void getAndBindRecords(Problem problem) {

        String query = generateQuery(TERM, "problemId", problem.getId());
        SearchResult res = search(query, RECORD);

        if (res != null) {

            RecordList records = new RecordList(res.getSourceAsObjectList(Record.class, true));
            problem.setRecords(records);

            for (Record record : records) {
                IrisProjectApplication.addRecordToCache(record);
                getAndBindRecordPhotos(record);
            }

        } else printError(Problem.class, problem.getId());

    }

    public void getAndBindRecordPhotos(Record record) {

        String query = generateQuery(TERM, "recordId", record.getId());
        SearchResult res = search(query, RECORDPHOTO);

        if (res != null) {

            List<RecordPhoto> recordPhotos = res.getSourceAsObjectList(RecordPhoto.class, true);
            record.setRecordPhotos(recordPhotos);

            for (RecordPhoto photo : recordPhotos) {
                photo.convertBlobToBitmap();
            }

        } else printError(Record.class, record.getId());

    }

    public void getAndBindBodyPhotos(Patient patient) {

        String query = generateQuery(TERM, "user", patient.getId());
        SearchResult res = search(query, BODYPHOTO);

        if (res != null) {

            List<BodyPhoto> bodyPhotos = res.getSourceAsObjectList(BodyPhoto.class, true);
            patient.setBodyPhotos(bodyPhotos);

            for (BodyPhoto photo : bodyPhotos) {
                photo.convertBlobToPhoto();
            }

        } else printError(Patient.class, patient.getId());

    }

    public void getAndBindComments(Problem problem) {

        String query = generateQuery(TERM, "problemId", problem.getId());
        SearchResult res = search(query, COMMENT);

        if (res != null) {

            List<Comment> comments = res.getSourceAsObjectList(Comment.class, true);
            problem.setComments(comments);

        } else printError(Problem.class, problem.getId());

    }

    /**
     * Returns a query string of specified lookup type and value to look for
     *
     * @param type Type of query (e.g. match, term)
     * @param key The key to check
     * @param value The value actually being looked for
     * @return The properly formatted query
     */
    public String generateQuery(String type, String key, String value) {
        return String.format("{\"query\": {\"%s\": {\"%s\": \"%s\"}}}", type, key, value);
    }

    /**
     * Executes an elasticsearch query and return its results
     *
     * @param query properly formatted Elasticsearch query
     * @param type The index type the query pertains to
     * @return The raw results
     */
    public SearchResult search(String query, String type) {

        try {

            Search get = new Search.Builder(query)
                    .addIndex(IrisProjectApplication.INDEX)
                    .addType(type)
                    .setParameter(Parameters.SIZE, IrisProjectApplication.SIZE)
                    .build();

            return IrisProjectApplication.getDB().execute(get);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * Prints a generic error to STDERR
     *
     * @param type The aggregate model's class (e.g. Record)
     * @param id The aggregate models' elasticsearch ID
     */
    private void printError(Class type, String id) {
        System.err.println(String.format("Could not get %s %s in %s.",
                type.getSimpleName(), id, this.getClass().getSimpleName()));
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        cb.onComplete(aVoid);
    }

}