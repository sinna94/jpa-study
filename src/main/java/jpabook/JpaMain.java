package jpabook;

import jpabook.entity.Member;

import javax.persistence.*;

public class JpaMain {
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");

    public static void main(String[] args) {

        

//        Member member = createMember("memberA", "회원1");
//        member.setUsername("회원명변경");
//        mergeMember(member);
    }

    private static void logic (EntityManager em){

    }

    private static void mergeMember(Member member) {
        // 영속성 컨텍스트2 시작
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        Member mergeMember = em.merge(member);
        tx.commit();

        //준 영속 상태
        System.out.println("member=" + member.getUsername());

        //영속 상태
        System.out.println("mergeMember=" + mergeMember.getUsername());

        System.out.println("em contains member = " + em.contains(member));
        System.out.println("em contains mergeMember = " + em.contains(mergeMember));

        em.close();
        // 영속성 컨텍스트 종료
    }

    private static Member createMember(String id, String username) {
        // 영속성 컨텍스트1 시작
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Member member = new Member();
        member.setId(id);
        member.setUsername(username);
        member.setAge(20);

        em.persist(member);
        tx.commit();

        em.close();

        // 영속성 컨텍스트 종료

        return member;
    }
}
