CREATE TABLE someday (
	id INT AUTO_INCREMENT,
    year INT ,
    month INT ,
    day INT 
);
  
CREATE TABLE income (  
    id INT AUTO_INCREMENT , 
    reason CHAR,
    sal INT,
    year INT ,
    month INT ,
    day INT
    
);
  
CREATE TABLE spending (  
    id INT AUTO_INCREMENT ,
    reason CHAR,
    out INT,
    year INT,
    month INT,
    day INT

);

CREATE TABLE accumulation (  
    id INT AUTO_INCREMENT,
    reason CHAR,
    acc INT,
    year INT,
    month INT,
    day INT
)