package jpabook;

import jpabook.entity.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");

    public static void main(String[] args) {

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            logic(em);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

    private static void logic(EntityManager em) {
        Member member1 = new Member();
        member1.setId("member1");
        member1.setUsername("user1");
        em.persist(member1);

        Product product = new Product();
        product.setId("product1");
        product.setName("product1");
        em.persist(product);

        MemberProduct memberProduct = new MemberProduct();
        memberProduct.setMember(member1);
        memberProduct.setProduct(product);
        memberProduct.setOrderAmount(2);
        em.persist(memberProduct);

        em.flush();

        MemberProductId memberProductId = new MemberProductId();
        memberProductId.setMember("member1");
        memberProductId.setProduct("product1");
        MemberProduct findMemberProduct = em.find(MemberProduct.class, memberProductId);
        System.out.println(findMemberProduct.getMember().getUsername());
    }
}
   