CREATE TABLE someday (
    day DATE PRIMARY KEY,
    day_001 INT,  
    day_002 INT,　　
    day_003 INT　　　
);
  
CREATE TABLE income (  
    id INT AUTO_INCREMENT , 
    reason CHAR,
    sal INT
    
);
  
CREATE TABLE spending (  
    id INT AUTO_INCREMENT ,
    reason CHAR,
    out INT
);

CREATE TABLE accumulation (  
    id INT AUTO_INCREMENT,
    reason CHAR,
    acc INT
);