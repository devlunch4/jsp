package student.dao;

import student.model.StudentVo;

public interface IStudentDao {
	StudentVo getLottoStudent();
	
	//����ü �̸� : StudentDaoMybatis.java
	//sqlmapper : mybatis/student.xml
	//mybatis/SqlMapConfig.xml�� sqlmapper �߰� �ʿ�
}
