
-- (Carlos 10/08/2016)

-- Fecha de creación de un tip
ALTER TABLE Tip ADD COLUMN creationDate TIMESTAMP;
-- Inicializamos los TIPs ya creados con la fecha actual (por no dejarlo en blanco)
UPDATE Tip SET creationDate = current_timestamp;

-- Almacenar referencia al usuario que creó el TIP
ALTER TABLE Tip ADD COLUMN userId INTEGER;
ALTER TABLE Tip ADD CONSTRAINT Tip_UserAccount_FK FOREIGN KEY (userId) REFERENCES UserAccount(id);

