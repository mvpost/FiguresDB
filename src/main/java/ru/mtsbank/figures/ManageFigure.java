package ru.mtsbank.figures;

import jakarta.persistence.criteria.*;
import org.hibernate.query.Query;
import ru.mtsbank.figures.definition.*;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

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
        manageFigure.listFiguresByColors("red");
    }

    /* Delete all records */
    public void deleteRecords() {
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            CriteriaBuilder cb = session.getCriteriaBuilder();

            CriteriaDelete<Figure> criteriaDeleteFigure = cb.createCriteriaDelete(Figure.class);
            criteriaDeleteFigure.from(Figure.class);
            session.createQuery(criteriaDeleteFigure).executeUpdate();

            CriteriaDelete<Color> criteriaDeleteColor = cb.createCriteriaDelete(Color.class);
            criteriaDeleteColor.from(Color.class);
            session.createQuery(criteriaDeleteColor).executeUpdate();

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
            session.persist(color);
            Figure figure = new Figure(figureType);
            figure.setColor(color);
            session.persist(figure);
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
            CriteriaQuery<Color> cq = session.getCriteriaBuilder().createQuery(Color.class);
            cq.from(Color.class);
            List<Color> colors = session.createQuery(cq).getResultList();
            System.out.println("=====All Colors=====");
            for (Color color : colors) {
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
            CriteriaQuery<Figure> cq = session.getCriteriaBuilder().createQuery(Figure.class);
            cq.from(Figure.class);
            List<Figure> figures = session.createQuery(cq).getResultList();
            System.out.println("=====All Figures=====");
            for (Figure figure : figures) {
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
        CriteriaBuilder cb = session.getCriteriaBuilder();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            final CriteriaQuery<Figure> criteria = cb.createQuery( Figure.class );
            final Root<Color> root = criteria.from( Color.class );

            criteria.select( root.get( Color_.FIGURE ) );
            criteria.where( cb.equal( root.get( Color_.NAME ), color ) );
            Query<Figure> query = session.createQuery(criteria);
            List<Figure> figures = query.getResultList();

            System.out.println("======" + color + " figures=====");

            for (Figure figure : figures) {
                System.out.print("Type: " + figure.getType());
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

