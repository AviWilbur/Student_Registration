

public class Student {
    private int academicId;
    private String name;
    private String birthDate;
    private String address;
    private String contactDetails;
    private String courseName;
    private int courseCode;

    
	public Student(int academicId, String name, String birthDate, String address, String contactDetails, String courseName, int courseCode) {
        this.academicId = academicId;
        this.name = name;
        this.birthDate = birthDate;
        this.address = address;
        this.contactDetails = contactDetails;
        this.courseName = courseName;
        this.courseCode = courseCode;
    }


    public void setAcademicId(int academicId) {
		this.academicId = academicId;
	}



	public void setName(String name) {
		this.name = name;
	}



	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}



	public void setAddress(String address) {
		this.address = address;
	}



	public void setContactDetails(String contactDetails) {
		this.contactDetails = contactDetails;
	}



	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}



	public void setCourseCode(int courseCode) {
		this.courseCode = courseCode;
	}


    
    public int getStudent(int id) {
        return academicId;
    }
    
    
    public int getAcademicId() {
        return academicId;
    }

    public String getName() {
        return name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getAddress() {
        return address;
    }

    public String getContactDetails() {
        return contactDetails;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getCourseCode() {
        return courseCode;
    }

    @Override
    public String toString() {
        return "Academic ID: " + academicId + "\n" +
               "Name: " + name + "\n" ;
    }
}
