package com.messagebird;

import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.NotFoundException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class GroupTest {

    private static MessageBirdServiceImpl messageBirdService;
    private static MessageBirdClient messageBirdClient;

    private static Group group;

    @BeforeClass
    public static void setUpClass() throws UnauthorizedException, GeneralException {
        String accessKey = System.getProperty("messageBirdAccessKey");

        messageBirdService = new MessageBirdServiceImpl(accessKey);
        messageBirdClient = new MessageBirdClient(messageBirdService);

        group = messageBirdClient.sendGroup(new GroupRequest("MBTESTGROUP"));
    }

    @AfterClass
    public static void tearDown() throws UnauthorizedException, GeneralException, NotFoundException {
        messageBirdClient.deleteGroup(group.getId());
    }

    @Test
    public void testDeleteGroupContact() throws UnauthorizedException, GeneralException, NotFoundException {
        ContactList contactList = messageBirdClient.listContacts();
        if (contactList.getItems().isEmpty()) {
            // Skip test if we don't have any contacts...
            return;
        }

        Contact contact = contactList.getItems().get(0);
        messageBirdClient.sendGroupContact(group.getId(), new String[]{contact.getId()});

        messageBirdClient.deleteGroupContact(group.getId(), contact.getId());
    }

    @Test
    public void testListGroups() throws UnauthorizedException, GeneralException {
        GroupList groupList = messageBirdClient.listGroups();

        assertSame(20, groupList.getLimit());
        assertSame(0, groupList.getOffset());
        assertNotSame(0, groupList.getTotalCount());
        assertNotNull(groupList.getItems().get(0).getId());
    }

    @Test
    public void testSendGroupContacts() throws UnauthorizedException, GeneralException, NotFoundException {
        ContactList contactList = messageBirdClient.listContacts();

        assertSame(0, group.getContacts().getTotalCount());

        messageBirdClient.sendGroupContact(group.getId(), new String[]{contactList.getItems().get(0).getId()});

        group = messageBirdClient.viewGroup(group.getId());
        assertSame(1, group.getContacts().getTotalCount());
    }

    @Test
    public void testUpdateGroup() throws UnauthorizedException, GeneralException, NotFoundException {
        assertNotEquals("another name", group.getName());

        messageBirdClient.updateGroup(group.getId(), new GroupRequest("another name"));

        group = messageBirdClient.viewGroup(group.getId());

        assertEquals("another name", group.getName());
    }

    @Test
    public void testViewGroup() throws UnauthorizedException, GeneralException, NotFoundException {
        group = messageBirdClient.viewGroup(group.getId());

        assertNotNull(group.getContacts());
    }
}
