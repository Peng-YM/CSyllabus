package com.peng1m.springboot.model;

import javax.persistence.*;

@Entity
@Table(name = "courses")
public class Course {
    @Id
    @Column(name = "courseid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int courseid;

    @Column(name = "course_name")
    private String course_name;

    @Column(name = "course_code")
    private String course_code;

    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "school", referencedColumnName = "schoolid")
    private School school;

    @Column(name = "description")
    private String description;

    @Column(name = "prof")
    private String professor;

    @Column(name = "last_modify")
    private String last_modify;

    public int getCourseid() {
        return courseid;
    }

    public void setCourseid(int courseid) {
        this.courseid = courseid;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getCourse_code() {
        return course_code;
    }

    public void setCourse_code(String course_code) {
        this.course_code = course_code;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String prof) {
        this.professor = prof;
    }

    public String getLast_modify() {
        return last_modify;
    }

    public void setLast_modify(String last_modify) {
        this.last_modify = last_modify;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getSyllabuspath() {
        return syllabuspath;
    }

    public void setSyllabuspath(String syllabuspath) {
        this.syllabuspath = syllabuspath;
    }

    @OneToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "author", referencedColumnName = "id")
    private User author;

    @Column(name = "syllabuspath")
    private String syllabuspath;

    public Course(String course_name, String course_code, School school, String description, String prof, String last_modify, User author, String syllabuspath) {
        this.course_name = course_name;
        this.course_code = course_code;
        this.school = school;
        this.description = description;
        this.professor = prof;
        this.last_modify = last_modify;
        this.author = author;
        this.syllabuspath = syllabuspath;
    }

    public Course() {
    }

    @Override
    public String toString() {
        return "Course [courseid=" + courseid + ", name=" + course_name + ", course_code=" + course_code + ",school=" + school.getSchool_name() + ",description=" + description
                + ",prof=" + professor + "]";
    }
}
