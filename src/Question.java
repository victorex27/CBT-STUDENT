

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author USER
 */
public class Question {

    private String question;
    private String a;
    private String b;
    private String c;
    private String d;
    private String e;

    private Question() {


    }

    public String getQuestion() {
        return question;
    }

    public String getA() {
        return a;
    }

    public String getB() {
        return b;
    }

    public String getC() {
        return c;
    }

    public String getD() {
        return d;
    }

    public String getE() {
        return e;
    }

    public static class Builder {

        private String question;
        private String a;
        private String b;
        private String c;
        private String d;
        private String e;

        public Builder(String question, String a, String b) {

            this.question = question;
            this.a = a;
            this.b = b;
        }
        
        public Builder addC(String c){
        
            this.c = c;
            return this;
        }
        
        public Builder addD(String d){
        
            this.d = d;
            return this;
        }
        
        public Builder addE(String e){
        
            this.e = e;
            return this;
        }
        
        public Question build(){
        
            Question q = new Question();
            q.question = this.question;
            q.a = this.a;
            q.b = this.b;
            q.c = this.c;
            q.d = this.d;
            q.e = this.e;
            
            return q;
        
        }

    }

}
