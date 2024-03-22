-- select() String sql TOP-N
SELECT *
FROM(
    SELECT ROWNUM no,t.*
    FROM (
        SELECT seq, title, writer, email, writeDate, readed
        FROM tbl_cstvsboard
        ORDER BY seq DESC
        )t
    )b
WHERE no BETWEEN 1 AND 10;

SELECT CEIL(COUNT(*)) / 10 -- 16∆‰¿Ã¡ˆ
FROM tbl_cstvsboard;

SELECT *
FROM(
    SELECT ROWNUM no,t.*
    FROM (
        SELECT seq, title, writer, email, writeDate, readed
        FROM tbl_cstvsboard
        ORDER BY seq DESC
        
        )t
    )b
WHERE no BETWEEN 1 AND 10;