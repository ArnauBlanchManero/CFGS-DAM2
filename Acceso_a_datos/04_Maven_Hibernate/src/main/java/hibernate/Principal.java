package hibernate;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class Principal {

	public static void main(String[] args) {
		Session sesion = HibernateUtil.getSf().openSession();
		Transaction t = sesion.beginTransaction();
		Persona arnau = new Persona("Arnau", 19);
		sesion.save(arnau);
		t.commit();
		sesion.close();
		HibernateUtil.shutdown();
	}

}
