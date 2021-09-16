package com.example.demo.quizPro;


public class Question {

    private Long id;
    private String content;
    private QuestionType type;
    private Object subject;

    public Question() {
    }

    public Question(Long id, String content, QuestionType type, Object subject) {
        this.id = id;
        this.content = content;
        this.type = type;
        this.subject = subject;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public QuestionType getType() {
        return type;
    }

    public void setType(QuestionType type) {
        this.type = type;
    }

    public Object getSubject() {
        return subject;
    }

    public void setSubject(Object subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", type=" + type +
                ", subject=" + subject +
                '}';
    }
}
