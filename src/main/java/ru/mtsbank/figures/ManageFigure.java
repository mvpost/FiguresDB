package ru.mtsbank.figures;

import ru.mtsbank.figures.definition.*;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.Iterator;
import java.util.List;

public class ManageFigure {
    private static SessionFactory factory;

    public static void main(String[] args) {

        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }

        /* Create figures */
        Rectangle rectangle = new Rectangle("red", 1, 5.0, 3.0);
        Circle circle = new Circle("green", 2, 2.5);
        Rhombus rhombus = new Rhombus("blue", 3, 4.0);

        ManageFigure manageFigure = new ManageFigure();

        /* Delete all records */
        manageFigure.deleteRecords();

        /* Add colors and figures in database */
        manageFigure.fillDatabase(rectangle.getColor(), 255, 0, 0, Rectangle.class.getSimpleName());
        manageFigure.fillDatabase(circle.getColor(), 0, 255, 0, Circle.class.getSimpleName());
        manageFigure.fillDatabase(rhombus.getColor(), 0, 0, 255, Rhombus.class.getSimpleName());

        /* List down all the colors */
        manageFigure.listColors();

        /* List down all the figures */
        manageFigure.listFigures();

        /* Selection of figures by color */
        manageFigure.listFiguresByColors("green");
    }

    /* Delete all records */
    public void deleteRecords() {
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("delete from Figure");
            query.executeUpdate();
            query = session.createQuery("delete from Color");
            query.executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void fillDatabase(String name, int redCode, int greenCode, int blueCode, String figureType) {
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Color color = new Color(name, redCode, greenCode, blueCode);
            session.save(color);
            Figure figure = new Figure(figureType);
            figure.setColor(color);
            session.save(figure);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    /* Read all the colors */
    public void listColors() {
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            List colors = session.createQuery("FROM Color").list();
            System.out.println("=====All Colors=====");
            for (Iterator iterator = colors.iterator(); iterator.hasNext(); ) {
                Color color = (Color) iterator.next();
                System.out.print("Name: " + color.getName());
                System.out.print("  Red Code: " + color.getRedCode());
                System.out.print("  Green Code: " + color.getGreenCode());
                System.out.println("  Blue Code: " + color.getBlueCode());
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    /* Read all the figures */
    public void listFigures() {
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            List figures = session.createQuery("FROM Figure").list();
            System.out.println("=====All Figures=====");
            for (Iterator iterator = figures.iterator(); iterator.hasNext(); ) {
                Figure figure = (Figure) iterator.next();
                System.out.print("Type: " + figure.getType());
                System.out.print("  Color Name: " + figure.getColor().getName());
                System.out.print("  R: " + figure.getColor().getRedCode());
                System.out.print("  G: " + figure.getColor().getGreenCode());
                System.out.println("  B: " + figure.getColor().getBlueCode());
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    /* Read figures by color */
    public void listFiguresByColors(String color) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List figures = session.createQuery("FROM Figure").list();
            System.out.println("======" + color + " figures=====");
            for (Iterator iterator = figures.iterator(); iterator.hasNext(); ) {
                Figure figure = (Figure) iterator.next();
                if (figure.getColor().getName().equals(color)) {
                    System.out.print("Type: " + figure.getType());
                }
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

}

