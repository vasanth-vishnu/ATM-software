package com.c;

import java.sql.*;
import java.util.Scanner;

public class Main {
    int k1, k2;
    String uslo, pwlo;
    String usr, pin, conpin, em, bn;
    String upus,upin,upincon,upem,upbn;
    int balance,yn,upta,upbal;
    public Connection con;
    public Statement st;
    public ResultSet rs;
    String us,pw;
    public void initial() {
        Scanner ob1 = new Scanner(System.in);
        System.out.println("WELCOME TO THE DIGITAL MONEY APPlICATION!!");
        System.out.println("1.login");
        System.out.println("2.register");
        int a;
        int  vas;
        a = ob1.nextInt();
        if (a == 1) {
            login2();
        } else if (a == 2) {
            System.out.println("Fill the following detailes for registration");
            System.out.println("Username : ");
            usr = ob1.next();
            System.out.println("Pin : ");
            pin = ob1.next();
            System.out.println("Conform your pin : ");
            conpin = ob1.next();
            if(pin.equals(conpin)) {
                System.out.println("Enter your email-id : ");
                em = ob1.next();
            }
            else{
                System.out.println("please enter the same password");
                System.out.println("Conform your pin : ");
                conpin = ob1.next();
                System.out.println("Enter your email-id : ");
                em = ob1.next();
            }
            System.out.println("Enter the bankaccount number :");
            bn = ob1.next();
            System.out.println("Enter your available amount in your bank account : ");
            balance = ob1.nextInt();
            System.out.println("You have successfully registered now press 1 to go back home and login , 0 to exit ");
            
            vas = ob1.nextInt();
            if (vas == 1)
            {
                login();
            }
            else
                {
                System.exit(0);
            }
        }
    }
    public void login2(){
        Scanner ob2 = new Scanner(System.in);
        System.out.print("Username :");
        uslo = ob2.next();
        System.out.print("Pin : ");
        pwlo = ob2.next();
        getdata();
    }

    public void login() {
        logincheck();
        Scanner ob2 = new Scanner(System.in);
        System.out.print("Username :");
        uslo = ob2.next();
        System.out.print("Pin : ");
        pwlo = ob2.next();
        getdata();
    }

    public void inputs() {
        System.out.println("1.Check Balance");
        System.out.println("2.With drawl");
        System.out.println("3.Deposite");
        System.out.println("4.Delete your account ");
        System.out.println("5.Update your account ");
        System.out.println("6. Exit");
    }

    public int checkbalance(int am) {

        return am;
    }

    public int withdrawl(int am, int amount) {
        Scanner obs = new Scanner(System.in);
        if (am < 500) {
            System.out.println("please enter the amount more than 500(minimum deposite)");
            k1 = obs.nextInt();
            withdrawl(k1, amount);
        } else if (am > amount) {
            System.out.println("your balance is less than entered amount please enter the valid amount");
            k2 = obs.nextInt();
            withdrawl(k2, amount);
        } else {
            System.out.println("Your withdrawl amount is" + am);
            System.out.println("your available balance :");
            System.out.println(amount - am);
        }
        return (amount - am);

    }

    public int deposit(int amm, int amo) {
        amo += amm;
        return amo;
    }
    public void connect(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/atm","root","");
            st=con.createStatement();
        }
        catch(Exception ex){
            System.out.println("Error: "+ex);
        }
    }
    public void logincheck(){
        try {
            String q = "insert into atmuser (username,pin,email,bankaccount,balance) values (?,?,?,?,?)";
            PreparedStatement ps=con.prepareStatement(q);
            ps.setString(1,usr);
            ps.setString(2,pin);
            ps.setString(3,em);
            ps.setString(4,bn);
            ps.setInt(5,balance);
            ps.executeUpdate();
            System.out.println("inserted ......");
        }
        catch (Exception ex){
            System.out.println(ex);
        }
    }
    public void updatet(String names,String us,String ups){
        try {
            String q8 = "update atmuser set "+names+"=? where username=?";
            PreparedStatement p1 = con.prepareStatement(q8);
            p1.setString(1,ups);
            p1.setString(2,us);
            p1.executeUpdate();
            System.out.println("updated successfully");
            System.out.println("Login again to see your updates");
            login2();
        }
        catch(Exception ex)
        {

            System.out.println(ex);
        }

    }
    public void getdata(){
        try {
            String query = "select * from atmuser";
            rs = st.executeQuery(query);
            while (rs.next()) {
                String name = rs.getString("username");
                String pin = rs.getString("pin");
                String em=rs.getString("email");
                String bn1=rs.getString("bankaccount");
                int balance=rs.getInt("balance");
                if(name.equals(uslo) && pin.equals(pwlo)){
                    System.out.println("Hello !!"+name);
                    System.out.println("Your account number is : "+bn1);
                    System.out.println("Available balance is : "+balance);
                    System.out.println("Your email account is : "+em);
                    System.out.println("Please select the numericals to interact with me");
                    loop(balance);
                }
            }
        }
        catch (Exception ex){
            System.out.println("error is"+ex);
        }
    }
    public void deldata(String s){
        try {
            String q5 = "delete from atmuser where username=?";
            PreparedStatement p=con.prepareStatement(q5);
            p.setString(1,s);
            p.executeUpdate();
            System.out.println("thank you "+s+"for using this application ");
            System.out.println("Your account is deactivated");
            System.exit(0);
        }
        catch(Exception ex){
            System.out.println(ex);
        }
    }
        public void upgenr(){
            Scanner ob7=new Scanner(System.in);
            System.out.println("Enter the new password : ");
            upin = ob7.next();
            System.out.println("PLease conform your password");
            upincon = ob7.next();
            if (upin.equals(upincon)) {
                updatet("pin", uslo, upin);
            }
            else {
                upgenr();
            }
        }

 public void loop(int bal){
     Scanner sc=new Scanner(System.in);
     int am=bal;
     while(true) {
         inputs();
         int n, w, w1;
         n = sc.nextInt();
         switch (n) {
             case 1:
                 int k = checkbalance(am);
                 System.out.println(k);
                 break;
             case 2:
                 System.out.println("enter the amount to be withdrawl");
                 w = sc.nextInt();
                 int k66=withdrawl(w,am);
                 am=k66;
                 break;
             case 3:
                 System.out.println("enter the amount to be deposited");
                 w1 = sc.nextInt();
                 int k55=deposit(w1,am);
                 System.out.println("your available balance is:"+k55);
                 am=k55;
                 break;
             case 4:
                 Scanner ob9=new Scanner(System.in);
                 System.out.println("are you sure that you want to delete your account");
                 System.out.println("1.yes");
                 System.out.println("2.no");
                 yn=ob9.nextInt();
                 if(yn==1){
                     deldata(uslo);
                 }
             case 5:
                 Scanner ob3=new Scanner(System.in);
                 System.out.println("update your account");
                 System.out.println("1.update username");
                 System.out.println("2.generate new pin");
                 System.out.println("3.update email");
                 System.out.println("4.ubdate bankaccount number");
                 upta=ob3.nextInt();
                 if(upta==1){
                     System.out.println("Enter the new username : ");
                     upus=ob3.next();
                         updatet("username",uslo,upus);
                 }
                 if(upta==2) {
                     upgenr();
                 }
                 if(upta==3){
                     System.out.println("ENter your new email id : ");
                     upem=ob3.next();
                     updatet("email",uslo,upem);
                 }
                 if(upta==4){
                     System.out.println("Enter your new bank account number : ");
                     upbn=ob3.next();
                     updatet("bankaccount",uslo,upbn);
                 }

             case 6:
                 System.exit(0);

         }
     }

 }

}

class Data extends Main {
    public static void main(String[] args) {
        Data ob3 = new com.c.Data();
        ob3.connect();
        ob3.initial();
        ob3.logincheck();
        ob3.getdata();
    }
}

