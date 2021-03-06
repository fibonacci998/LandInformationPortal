package capstone.lip.landinformationportal.business.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import capstone.lip.landinformationportal.common.entity.CrawledNews;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

@Repository
public interface CrawledNewsRepository extends JpaRepository<CrawledNews, Long>, JpaSpecificationExecutor<CrawledNews>{
	CrawledNews findByCrawledNewsLink(String link);
	List<CrawledNews> findByCrawledNewsStatus(String status);
	Page<CrawledNews> findByCrawledNewsStatus(String status, Pageable page);
	Long countByCrawledNewsStatus(String status);
}
