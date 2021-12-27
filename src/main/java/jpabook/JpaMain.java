package jpabook;

import jpabook.entity.Member;
import jpabook.entity.Team;

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
        // 연관 관계 추가
        Team team = new Team("team1", "Team1");
        em.persist(team);

        Member member1 = new Member("member1", "Member1");
        member1.setTeam(team);
        em.persist(member1);

        Member member2 = new Member("member2", "Member2");
        member2.setTeam(team);
        em.persist(member2);

        // 연관 관계 조회
        String jpql = "select m from Member m join m.team t where t.name=:teamName";
        List<Member> resultList = em.createQuery(jpql, Member.class)
                .setParameter("teamName", "Team1")
                .getResultList();

        for (Member member : resultList) {
            System.out.println("[query] member.username=" + member.getUsername());
            System.out.println("[query] member.teamId=" + member.getTeam().getId());
        }

        // 연관 관계 수정
        Team team2 = new Team("team2", "Team2");
        em.persist(team2);

        Member member = em.find(Member.class, "member1");
//        member.setTeam(team2);

        // 연관 관계 제거
//        member.setTeam(null);

        // 일대다 방향으로 객체 그래프 탐색
        Team team1 = em.find(Team.class, "team1");
        List<Member> members = team1.getMembers();
        for (Member m : members) {
            System.out.println("member.username=" + m.getUsername());
        }
    }
}
