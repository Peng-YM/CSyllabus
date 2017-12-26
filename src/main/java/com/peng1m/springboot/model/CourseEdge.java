package com.peng1m.springboot.model;

import javax.persistence.*;

@Entity
@Table(name = "edges")
public class CourseEdge {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "edgeid")
    private int edgeid;

    @OneToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "source", referencedColumnName = "courseid")
    private Course source;

    @OneToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "target", referencedColumnName = "courseid")
    private Course target;

    @OneToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "school", referencedColumnName = "schoolid")
    private School school;

    public int getSourceId() {
        return getSource().getCourseid();
    }

    public int getTargetId() {
        return getTarget().getCourseid();
    }

    public int getEdgeid() {
        return edgeid;
    }

    public void setEdgeid(int edgeid) {
        this.edgeid = edgeid;
    }

    public Course getSource() {
        return source;
    }

    public void setSource(Course source) {
        this.source = source;
    }

    public Course getTarget() {
        return target;
    }

    public void setTarget(Course target) {
        this.target = target;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }
}
