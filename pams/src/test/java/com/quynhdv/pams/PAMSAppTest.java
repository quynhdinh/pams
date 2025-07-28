package com.quynhdv.pams;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.quynhdv.pams.model.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PAMSAppTest {
    
    private List<Patient> patients;
    private ObjectWriter objectWriter;
    private ObjectMapper objectMapper;
    
    @BeforeEach
    void setUp() {
        patients = new ArrayList<>();
        patients.add(new Patient("Daniel", "Agar", "(641) 123-0009", "dagar@m.as", "1 N Street", LocalDate.of(1987, 1, 19)));
        patients.add(new Patient("Ana", "Smith", "(641) 123-0009", "amsith@te.edu", null, LocalDate.of(1948, 12, 5)));
        patients.add(new Patient("Marcus", "Garvey", null, "(123) 292-0018", "4 East Ave", LocalDate.of(2001, 9, 18)));
        patients.add(new Patient("Jeff", "Goldbloom", "(999) 165-1192", "jgold@es.co.za", null, LocalDate.of(1995, 2, 28)));
        patients.add(new Patient("Mary", "Washington", "30 W Burlington", null, null, LocalDate.of(1932, 5, 31)));
        
        objectMapper = new ObjectMapper();
        objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
    }
    
    @Test
    void testPatientCreation() {
        assertNotNull(patients);
        assertEquals(5, patients.size());
        
        Patient daniel = patients.get(0);
        assertEquals("Daniel", daniel.getFirstName());
        assertEquals("Agar", daniel.getLastName());
        assertEquals("(641) 123-0009", daniel.getContactNumber());
        assertEquals("dagar@m.as", daniel.getEmail());
        assertEquals("1 N Street", daniel.getMailingAddress());
    }
    
    @Test
    void testPatientAgeCalculation() {
        Patient daniel = patients.get(0); // Born in 1987
        Patient ana = patients.get(1);    // Born in 1948
        Patient marcus = patients.get(2); // Born in 2001
        
        int danielAge = daniel.getAge();
        int anaAge = ana.getAge();
        int marcusAge = marcus.getAge();
        
        // Verify age calculation (current year - birth year)
        assertEquals(2025 - 1987, danielAge);
        assertEquals(2025 - 1948, anaAge);
        assertEquals(2025 - 2001, marcusAge);
        
        // Verify sorting logic
        assertTrue(marcusAge < danielAge);
        assertTrue(danielAge < anaAge);
    }
    
    @Test
    void testPatientSortingByAge() {
        var sortedList = patients.stream()
                .sorted((x, y) -> Integer.compare(x.getAge(), y.getAge()))
                .toList();
        
        assertEquals(5, sortedList.size());
    
        // Verify the sorted order (youngest to oldest)
        // Marcus (2001) should be first, Mary (1932) should be last
        assertEquals("Marcus", sortedList.get(0).getFirstName());
        assertEquals("Mary", sortedList.get(sortedList.size() - 1).getFirstName());
        
        // Verify ages are in ascending order
        for (int i = 0; i < sortedList.size() - 1; i++) {
            assertTrue(sortedList.get(i).getAge() <= sortedList.get(i + 1).getAge());
        }
    }
    
    @Test
    void testJsonSerialization() throws JsonProcessingException {
        Patient daniel = patients.get(0);
        String json = objectWriter.writeValueAsString(daniel);
        
        assertNotNull(json);
        assertTrue(json.contains("Daniel"));
        assertTrue(json.contains("Agar"));
        assertTrue(json.contains("dagar@m.as"));
        assertTrue(json.contains("1 N Street"));
        
        // Verify that age is NOT included in JSON (due to @JsonIgnore)
        assertFalse(json.contains("age"));
        
        // Parse JSON to verify structure
        JsonNode jsonNode = objectMapper.readTree(json);
        assertEquals("Daniel", jsonNode.get("firstName").asText());
        assertEquals("Agar", jsonNode.get("lastName").asText());
        assertEquals("(641) 123-0009", jsonNode.get("contactNumber").asText());
        assertEquals("dagar@m.as", jsonNode.get("email").asText());
        assertEquals("1 N Street", jsonNode.get("mailingAddress").asText());
        assertNotNull(jsonNode.get("dateOfBirth"));
        
        // Verify age field is not present
        assertNull(jsonNode.get("age"));
    }
}
