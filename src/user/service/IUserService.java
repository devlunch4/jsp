package user.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import user.model.UserVo;

public interface IUserService {
	//��ü ����� ���� ��ȸ
	public List<UserVo> getUserList() throws SQLException;
	
	//��ü ����� ���� ��ȸ(����¡)
	public List<UserVo> getUserListPaging(Map<String, String> map) throws SQLException;
	
	//����� ��ü �Ǽ� ��ȸ
	public int getUserTotalCnt() throws SQLException;

	//����� ����
	public boolean checkLogin(Map<String, String> userinfo);
	
	//����� ��ȸ
	public UserVo getUser(Map<String, String> userinfo) throws SQLException;
	
	//����� ����
	public int deleteUser(Map<String, String> userinfo) throws SQLException;
	
	//����� ����
	public int updateUser(UserVo userVo) throws SQLException;
	
	//����� �Է�
	public int insertUser(UserVo userVo) throws SQLException;
	
	//����� �ߺ�üũ
	public int checkDupId(String userId) throws SQLException;
}
