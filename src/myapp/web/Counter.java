package myapp.web;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Amdrii
 */
public class Counter {
    private static int count;
    public static int getCount(){
        return count++;
    }
}
