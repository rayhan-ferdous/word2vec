    public void testCasting() {

        List students = new ArrayList();

        students.add(new Student("a"));

        students.add(new Student("b"));

        List names = new ArrayList();

        Iterator it = students.iterator();

        while (it.hasNext()) {

            Student student = (Student) it.next();

            names.add(student.getLastName());

        }

        assertEquals("a", names.get(0));

        assertEquals("b", names.get(1));

    }
