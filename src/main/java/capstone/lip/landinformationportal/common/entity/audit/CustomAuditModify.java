package capstone.lip.landinformationportal.common.entity.audit;
import java.sql.Timestamp;

public interface CustomAuditModify {
	public Timestamp getCreatedDate();

	public void setCreateDate(Timestamp createdDate);

	public Timestamp getModifiedDate();

	public void setModifiedDate(Timestamp modifiedDate);
}
