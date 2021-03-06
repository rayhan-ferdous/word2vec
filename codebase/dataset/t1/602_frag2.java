    public void testCommas() {

        String sequence = "1,2,3,4,5";

        assertEquals(sequence, sequenceUsingDo(1, 5));

        assertEquals(sequence, sequenceUsingFor(1, 5));

        assertEquals(sequence, sequenceUsingWhile(1, 5));

        sequence = "8";

        assertEquals(sequence, sequenceUsingDo(8, 8));

        assertEquals(sequence, sequenceUsingFor(8, 8));

        assertEquals(sequence, sequenceUsingWhile(8, 8));

    }
