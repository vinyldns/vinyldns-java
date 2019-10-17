package io.vinyldns.java.models.batch;

import static org.testng.Assert.*;

import io.vinyldns.java.model.batch.AddChangeInput;
import io.vinyldns.java.model.batch.DeleteRecordSetChangeInput;
import io.vinyldns.java.model.record.RecordType;
import io.vinyldns.java.model.record.data.AData;
import org.testng.annotations.Test;

public class ChangeInputTest {
    @Test
    public void testEquality() {
        // Add change input
        AddChangeInput addChange1 = new AddChangeInput("equality.test.", RecordType.A, 300L, new AData("1.2.3.4"));
        AddChangeInput addChange2 = new AddChangeInput("equality.test.", RecordType.A, 300L, new AData("1.2.3.4"));
        AddChangeInput addChange3 = new AddChangeInput("an.equality.test.", RecordType.A, 300L, new AData("1.2.3.4"));
        AddChangeInput addChange4 = new AddChangeInput("equality.test.", RecordType.A, 300L, new AData("1.2.3.5"));
        assertEquals(addChange1, addChange2);
        assertNotEquals(addChange1, addChange3);
        assertNotEquals(addChange1, addChange4);

        // Delete change input
        DeleteRecordSetChangeInput deleteChange1 = new DeleteRecordSetChangeInput("equality.test.", RecordType.A, new AData("1.2.3.4"));
        DeleteRecordSetChangeInput deleteChange2 = new DeleteRecordSetChangeInput("equality.test.", RecordType.A, new AData("1.2.3.4"));
        DeleteRecordSetChangeInput deleteChange3 = new DeleteRecordSetChangeInput("an.equality.test.", RecordType.A, new AData("1.2.3.4"));
        DeleteRecordSetChangeInput deleteChange4 = new DeleteRecordSetChangeInput("equality.test.", RecordType.A, new AData("1.2.3.5"));
        assertEquals(deleteChange1, deleteChange2);
        assertNotEquals(deleteChange1, deleteChange3);
        assertNotEquals(deleteChange1, deleteChange4);

        assertNotEquals(addChange1, deleteChange1);
    }
}
