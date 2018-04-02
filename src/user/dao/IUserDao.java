package user.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import user.model.UserVo;

public interface IUserDao {
	
	//��ü ����� ���� ��ȸ
	public List<UserVo> getUserList(SqlSession sqlSession) throws SQLException;
	
	//��ü ����� ���� ��ȸ(����¡)
	public List<UserVo> getUserListPaging(SqlSession sqlSession, Map<String, String> map) throws SQLException;
	
	//����� ��ü �Ǽ� ��ȸ
	public int getUserTotalCnt(SqlSession sqlSession) throws SQLException;

	//����� ����
	public boolean checkLogin(SqlSession sqlSession, Map<String, String> userinfo);
	
	//����� ��ȸ
	public UserVo getUser(SqlSession sqlSession, Map<String, String> userinfo) throws SQLException;
	
	//����� ����
	public int deleteUser(SqlSession sqlSession, Map<String, String> userinfo) throws SQLException;
	
	//����� ����
	public int updateUser(SqlSession sqlSession, UserVo userVo) throws SQLException;
	
	//����� �Է�
	public int insertUser(SqlSession sqlSession, UserVo userVo) throws SQLException;
	
	//����� �ߺ�üũ
	public int checkDupId(SqlSession sqlSession, String userId) throws SQLException;
}
