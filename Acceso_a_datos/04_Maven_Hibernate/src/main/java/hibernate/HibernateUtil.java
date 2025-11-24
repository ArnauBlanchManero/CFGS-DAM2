package hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	private static final SessionFactory  sf = bouldSessionFactory();
	public static SessionFactory bouldSessionFactory() {
		try {
			return new Configuration().configure().buildSessionFactory();
		} catch (Throwable e) {
			System.err.println("Error en buildSessionFactory: \n"+e);
			throw new ExceptionInInitializerError(e);
		}
	}
	public static SessionFactory getSf() {
		return sf;
	}
	
	public static void shutdown() {
		getSf().close();
	}

}
