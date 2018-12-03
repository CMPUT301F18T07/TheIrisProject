package com.team7.cmput301.android.theirisproject;

import com.team7.cmput301.android.theirisproject.model.BodyPhoto;
import com.team7.cmput301.android.theirisproject.model.Comment;
import com.team7.cmput301.android.theirisproject.model.Contact;
import com.team7.cmput301.android.theirisproject.model.Problem;
import com.team7.cmput301.android.theirisproject.model.Record;
import com.team7.cmput301.android.theirisproject.model.RecordList;
import com.team7.cmput301.android.theirisproject.model.RecordPhoto;
import com.team7.cmput301.android.theirisproject.model.User;
import com.team7.cmput301.android.theirisproject.model.User.UserType;


import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.fail;

public class ProblemTest {

    private String title;
    private String description;
    private String userid;
    private RecordList records;

    private String _id;
    private String problemId;
    private String author;
    private String role;
    private Date date;
    private String body;

    private String blob;

    @Test
    public void testProblem() {
        // Test creating problem

        Problem problem = getTestProblem();
        Assert.assertEquals(title, problem.getTitle());
        Assert.assertEquals(description, problem.getDescription());
        Assert.assertEquals(records, problem.getRecords());
    }

    @Test
    public void testGetSlideshowInfo() {
        // Test getting slideshow information

        Problem problem = getTestProblem();
        List<RecordPhoto> test_photos = problem.getSlideShowInfo();

        for (Record record : records) {
            List<RecordPhoto> photos = record.getRecordPhotos();
            for (RecordPhoto photo : photos) {
                Assert.assertTrue(test_photos.contains(photo));
            }
        }

    }

    @Test
    public void testAddComment() {
        // Test adding a comment to problem

        Problem problem = getTestProblem();
        Comment comment = getTestComment();

        problem.addComment(comment);
        Assert.assertEquals(problem.getComments().size(), 1);
        Assert.assertEquals(problem.getComments().get(0), comment);
    }

    @Test
    public void testIncorrectTitle() {
        // Test catch exception of invalid problem title

        try {
            String title = "longtitlelongtitlelongtitlelongtitle";
            String description = "I think I'm slowly turning into a zombie.";
            String userid = "0";
            RecordList records = new RecordList();

            Problem problem = new Problem(title, description, userid);
            fail("Should throw an exception if title length exceeds 30 characters");
        } catch (Exception e) {
            assert(true);
        }
    }

    @Test
    public void testIncorrectDescription() {
        // Test catch exception of invalid description

        try {
            String title = "Title";
            String description = "longdescriptionlongdescriptionlongdescriptionlongdescriptionlongdescriptionlongdescriptionlongdescriptionlongdescriptionlongdescriptionlongdescriptionlongdescriptionlongdescriptionlongdescriptionlongdescriptionlongdescriptionlongdescriptionlongdescriptionlongdescriptionlongdescriptionlongdescriptionlongdescriptionlongdescriptionlongdescriptionlongdescription";
            String userid = "0";
            RecordList records = new RecordList();

            Problem problem = new Problem(title, description, userid);
            fail("Should throw an exception if description length exceeds 300 characters");
        } catch (Exception e) {
            assert(true);
        }
    }

    private Problem getTestProblem() {

        String title = "Zombification";
        String description = "I think I'm slowly turning into a zombie.";
        String userid = "0";
        RecordList records = new RecordList();

        return new Problem(title, description, userid);
    }

    private Comment getTestComment() {

        String _id = "mememe";
        String problemId = "mrmrmr";
        Contact contact = new Contact("UserOne", "UserOne@email.com", "123-123-1234");
        UserType role = UserType.CARE_PROVIDER;
        Date date = new Date();
        String body = "Zombies aren't real";

        return new Comment(problemId, contact, body, role);
    }
}

