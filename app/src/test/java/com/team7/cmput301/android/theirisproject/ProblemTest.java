package com.team7.cmput301.android.theirisproject;
import com.team7.cmput301.android.theirisproject.model.Problem;

import junit.framework.Assert;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ProblemTest {

    private String title = "Zombification";
    private String description = "I think I'm slowly turning into a zombie.";
    private RecordList records = new RecordList();
    private List<BodyPhoto> body_photos = new ArrayList<>();

    @Test
    public void testProblem() {
        Problem problem = new Problem(title, description, records, body_photos);
        Assert.assertEquals(title, problem.getTitle());
        Assert.assertEquals(description, problem.getDescription());
        Assert.assertEquals(body_photos, problem.getBodyPhotos());
        Assert.assertEquals(records, problem.getRecords());
    }

    @Test
    public void testGetSlideshowInfo() {

        Problem problem = new Problem(title, description, records, body_photos);
        List<RecordPhoto> test_photos = problem.getSlideShowInfo();

        for (Record record: records){
            List<RecordPhoto> photos = record.getPhotos();
            for (RecordPhoto photo: photos){
                Assert.assertTrue(test_photos.contains(photo));
            }
        }

    }
}

