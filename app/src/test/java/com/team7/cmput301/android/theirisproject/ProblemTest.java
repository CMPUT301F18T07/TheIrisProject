package com.team7.cmput301.android.theirisproject;
import com.team7.cmput301.android.theirisproject.model.BodyPhoto;
import com.team7.cmput301.android.theirisproject.model.Comment;
import com.team7.cmput301.android.theirisproject.model.Problem;
import com.team7.cmput301.android.theirisproject.model.Record;
import com.team7.cmput301.android.theirisproject.model.RecordList;
import com.team7.cmput301.android.theirisproject.model.RecordPhoto;
import com.team7.cmput301.android.theirisproject.model.User;


import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProblemTest {

    private String title;
    private String description;
    private String userid;
    private RecordList records;
    private List<BodyPhoto> body_photos;

    private String _id;
    private String problemId;
    private String author;
    private String role;
    private Date date;
    private String body;

    private String blob;

    @Test
    public void testProblem() {
        Problem problem = getTestProblem();
        Assert.assertEquals(title, problem.getTitle());
        Assert.assertEquals(description, problem.getDescription());
        Assert.assertEquals(body_photos, problem.getBodyPhotos());
        Assert.assertEquals(records, problem.getRecords());
    }

    @Test
    public void testGetSlideshowInfo() {

        Problem problem = getTestProblem();
        List<RecordPhoto> test_photos = problem.getSlideShowInfo();

        for (Record record: records){
            List<RecordPhoto> photos = record.getRecordPhotos();
            for (RecordPhoto photo: photos){
                Assert.assertTrue(test_photos.contains(photo));
            }
        }

    }

    @Test
    public void testAddComment() {

        Problem problem = getTestProblem();
        Comment comment = getTestComment();

        problem.addComment(comment);
        Assert.assertEquals(problem.getComments().size(), 1);
    }

    @Test
    public void testAddBodyPhoto() {

        Problem problem = getTestProblem();
        BodyPhoto bodyPhoto = getTestBodyPhoto();

        problem.addBodyPhoto(bodyPhoto);
        Assert.assertEquals(problem.getBodyPhotos().size(), 1);
        Assert.assertEquals(problem.getBodyPhotos(), bodyPhoto);
    }

    private Problem getTestProblem() {

        String title = "Zombification";
        String description = "I think I'm slowly turning into a zombie.";
        String userid = "0";
        RecordList records = new RecordList();
        List<BodyPhoto> body_photos = new ArrayList<>();

        return new Problem(title, description, userid, body_photos);
    }

    private Comment getTestComment() {

        String _id = "mememe";
        String problemId = "mrmrmr";
        String author = "John";
        User.UserType role = User.UserType.CARE_PROVIDER;
        Date date = new Date();
        String body = "Zombies aren't real";

        return new Comment(problemId, author, body, role);
    }

    private BodyPhoto getTestBodyPhoto() {

        String problemId = "haba14";
        String blob = "blob";

        return new BodyPhoto(problemId, blob);
    }
}

