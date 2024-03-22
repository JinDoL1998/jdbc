-- ID 중복체크하는 저장프로시저
-- emp 테이블의 empno를 ID로 생각하고 구현
CREATE OR REPLACE PROCEDURE up_idcheck
(
    pid IN emp.empno%TYPE
    ,pcheck OUT NUMBER
)
IS
BEGIN
    SELECT COUNT(*) INTO pcheck
    FROM emp
    WHERE empno = pid;
--EXCEPTION
END;

-- (꼭 기억) 저장프로시저를 테르스를 한 후에 자바코딩에서 사용하자
DECLARE
    vcheck NUMBER(1);
BEGIN
    up_idcheck(8888, vcheck);
    DBMS_OUTPUT.PUT_LINE(vcheck);
END;

-- 로그인 체크
CREATE OR REPLACE PROCEDURE up_login
(
    pid IN NUMBER
    ,ppwd IN emp.ename%TYPE
    ,ploginCheck OUT NUMBER -- 1(성공), 0(실패)
)
IS
    vpwd emp.ename%TYPE;
BEGIN
    SELECT COUNT(*) INTO ploginCheck
    FROM emp
    WHERE empno = pid;
    
    IF ploginCheck = 1 THEN      -- ID 존재 O
        SELECT ename INTO vpwd
        FROM emp
        WHERE empno = pid;
        
        IF vpwd = ppwd THEN -- PWD 일치
            ploginCheck := 0;
        ELSE
            ploginCheck := 1;
        END IF;
        
    ELSE                    -- ID 존재 X
        ploginCheck := -1;
    END IF;
--EXCEPTION
END;

DECLARE
    vloginCheck NUMBER(1);
BEGIN
    up_login(7777, 'jinseong', vloginCheck);
    DBMS_OUTPUT.PUT_LINE(vloginCheck);
END;




-- 조회 day04.EX05
CREATE OR REPLACE PROCEDURE up_selectdept
(
    pdeptcursor OUT SYS_REFCURSOR
)
IS
BEGIN
    -- OPEN 커서 FOR문
    OPEN pdeptcursor FOR SELECT * FROM dept;
    
--EXCEPTION
END;

-- 시퀀스 확인
SELECT *
FROM user_sequences;
-- 시퀀스 생성
CREATE SEQUENCE seq_deptno
INCREMENT BY 10
START WITH 50
NOCACHE;

-- 부서 insert day04.EX05
CREATE OR REPLACE PROCEDURE up_insertdept
(
    pdname dept.dname%TYPE := NULL
    ,ploc dept.loc%TYPE := NULL
)
IS
BEGIN
    INSERT INTO dept(deptno, dname, loc) 
    VALUES(seq_deptno.nextval, pdname, ploc);
    
    COMMIT;
--EXCEPTION
END;

SELECT * FROM dept;

-- day04.Ex05 수정
CREATE OR REPLACE PROCEDURE up_updatedept
(
    pdeptno dept.deptno%TYPE
    ,pdname dept.dname%TYPE := NULL
    ,ploc dept.loc%TYPE := NULL
)
IS
BEGIN
    UPDATE dept
    SET dname = NVL(pdname, dname), loc = NVL(ploc, loc)
    WHERE deptno = pdeptno;
    
    COMMIT;
--EXCEPTION
END;

-- day04.Ex05 삭제
CREATE OR REPLACE PROCEDURE up_deletedept
(
    pdeptno dept.deptno%TYPE
)
IS
BEGIN
    DELETE dept
    WHERE deptno = pdeptno;
    
    COMMIT;
--EXCEPTION
END;

SELECT * FROM dept;


CREATE TABLE tbl_cstVSBoard(
    seq NUMBER NOT NULL PRIMARY KEY -- 글 일련번호(PK)
    ,writer VARCHAR2(20) NOT NULL
    ,pwd VARCHAR2(20) NOT NULL
    ,email VARCHAR2(100)
    ,title VARCHAR2(200) NOT NULL
    ,writedate DATE DEFAULT SYSDATE
    ,readed NUMBER DEFAULT 0
    ,tag NUMBER(1) NOT NULL
    ,content CLOB 
);
-- Table TBL_CSTVSBOARD이(가) 생성되었습니다.

CREATE SEQUENCE SEQ_TBL_CSTVSBOARD
NOCACHE;
--Sequence SEQ_TBL_CSTVSBOARD이(가) 생성되었습니다.

-- 150개의 게시글의 임의로 추가
BEGIN
    FOR i IN 1.. 150
    LOOP
        INSERT INTO TBL_CSTVSBOARD
        (seq, writer, pwd, email, title, tag, content)
        VALUES
        (SEQ_TBL_CSTVSBOARD.NEXTVAL, '홍길동' || i, '1234'
        , 'hong' || i || '@sist.com'
        , 'title-' || i
        , 0
        , 'content-' || i);
    END LOOP;
    COMMIT;
END;

BEGIN 
    UPDATE TBL_CSTVSBOARD
    SET writer = '유진'
    WHERE MOD (seq, 15) = 2;
    -- WHERE MOD(seq, 7) = 1;
    COMMIT;
END;

BEGIN 
    UPDATE TBL_CSTVSBOARD
    SET title = '저장 프로시저'
    WHERE MOD (seq, 9) IN (3,5);
    COMMIT;
END;

DESC tbl_cstvsboard;
SELECT seq, title, writer, email, writeDate, readed 
					FROM tbl_cstvsboard 
                    ORDER BY seq DESC;
                    
SELECT *
FROM tbl_cstvsboard
ORDER BY seq DESC;