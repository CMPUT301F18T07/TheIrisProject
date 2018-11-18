package com.team7.cmput301.android.theirisproject;
import com.team7.cmput301.android.theirisproject.model.BodyPhoto;
import com.team7.cmput301.android.theirisproject.model.Problem;
import com.team7.cmput301.android.theirisproject.model.Record;
import com.team7.cmput301.android.theirisproject.model.RecordList;
import com.team7.cmput301.android.theirisproject.model.RecordPhoto;


import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ProblemTest {

    private String title = "Zombification";
    private String description = "I think I'm slowly turning into a zombie.";
    private String userid = "0";
    private RecordList records = new RecordList();
    private List<BodyPhoto> body_photos = new ArrayList<>();

    @Test
    public void testProblem() {
        Problem problem = new Problem(title, description, userid ,records, body_photos);
        Assert.assertEquals(title, problem.getTitle());
        Assert.assertEquals(description, problem.getDescription());
        Assert.assertEquals(body_photos, problem.getBodyPhotos());
        Assert.assertEquals(records, problem.getRecords());
    }

    @Test
    public void testGetSlideshowInfo() {

        Problem problem = new Problem(title, description, userid, records, body_photos);
        List<RecordPhoto> test_photos = problem.getSlideShowInfo();

        for (Record record: records){
            List<RecordPhoto> photos = record.getRecordPhotos();
            for (RecordPhoto photo: photos){
                Assert.assertTrue(test_photos.contains(photo));
            }
        }

    }
}

