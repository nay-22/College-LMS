-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema lms
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema lms
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `lms` DEFAULT CHARACTER SET utf8 ;
USE `lms` ;

-- -----------------------------------------------------
-- Table `lms`.`Student`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `lms`.`Student` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `roll_no` VARCHAR(128) NOT NULL,
  `first_name` VARCHAR(128) NOT NULL,
  `middle_name` VARCHAR(128) NULL,
  `last_name` VARCHAR(128) NOT NULL,
  `phone` VARCHAR(10) NULL,
  `email` VARCHAR(255) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `semester` INT NOT NULL,
  `dob` DATETIME NULL,
  `address` VARCHAR(255) NULL,
  `created_at` TIMESTAMP(2) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC),
  UNIQUE INDEX `roll_no_UNIQUE` (`roll_no` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `lms`.`Staff`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `lms`.`Staff` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(128) NOT NULL,
  `last_name` VARCHAR(128) NOT NULL,
  `middle_name` VARCHAR(128) NULL,
  `emp_no` VARCHAR(128) NULL,
  `email` VARCHAR(255) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `address` VARCHAR(255) NULL,
  `dob` DATETIME NULL,
  `phone` VARCHAR(10) NULL,
  `created_at` TIMESTAMP(2) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC),
  UNIQUE INDEX `roll_no_UNIQUE` (`emp_no` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `lms`.`AssignedStaff`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `lms`.`AssignedStaff` (
  `student_id` INT NOT NULL,
  `staff_id` INT NOT NULL,
  INDEX `fk_AssignedStaff_Student_idx` (`student_id` ASC),
  INDEX `fk_AssignedStaff_Staff1_idx` (`staff_id` ASC),
  CONSTRAINT `fk_AssignedStaff_Student`
    FOREIGN KEY (`student_id`)
    REFERENCES `lms`.`Student` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_AssignedStaff_Staff1`
    FOREIGN KEY (`staff_id`)
    REFERENCES `lms`.`Staff` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `lms`.`Courses`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `lms`.`Courses` (
  `id` INT NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `thumbnail` VARCHAR(255) NOT NULL,
  `semester` INT NOT NULL,
  `department` VARCHAR(256) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `lms`.`Content`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `lms`.`Content` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(256) NOT NULL,
  `type` VARCHAR(128) NOT NULL,
  `url` VARCHAR(512) NOT NULL,
  `course_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `url_UNIQUE` (`url` ASC),
  INDEX `fk_CourseContent_Courses1_idx` (`course_id` ASC),
  CONSTRAINT `fk_CourseContent_Courses1`
    FOREIGN KEY (`course_id`)
    REFERENCES `lms`.`Courses` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `lms`.`Enrollment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `lms`.`Enrollment` (
  `student_id` INT NOT NULL,
  `course_id` INT NOT NULL,
  INDEX `fk_CourseInstructor_Student1_idx` (`student_id` ASC),
  INDEX `fk_CourseInstructor_Courses1_idx` (`course_id` ASC),
  CONSTRAINT `fk_CourseInstructor_Student1`
    FOREIGN KEY (`student_id`)
    REFERENCES `lms`.`Student` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_CourseInstructor_Courses1`
    FOREIGN KEY (`course_id`)
    REFERENCES `lms`.`Courses` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `lms`.`Instructor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `lms`.`Instructor` (
  `course_id` INT NOT NULL,
  `staff_id` INT NOT NULL,
  INDEX `fk_CourseInstructor_Courses1_idx` (`course_id` ASC),
  INDEX `fk_CourseInstructor_copy1_Staff1_idx` (`staff_id` ASC),
  CONSTRAINT `fk_CourseInstructor_Courses10`
    FOREIGN KEY (`course_id`)
    REFERENCES `lms`.`Courses` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_CourseInstructor_copy1_Staff1`
    FOREIGN KEY (`staff_id`)
    REFERENCES `lms`.`Staff` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `lms`.`MessageInbox`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `lms`.`MessageInbox` (
  `student_id` INT NOT NULL,
  `staff_id` INT NOT NULL,
  `message` VARCHAR(4096) NOT NULL,
  `sent_from_student` TINYINT NOT NULL DEFAULT 1,
  `created_at` TIMESTAMP(2) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  INDEX `fk_AssignedStaff_Student_idx` (`student_id` ASC),
  INDEX `fk_AssignedStaff_Staff1_idx` (`staff_id` ASC),
  CONSTRAINT `fk_AssignedStaff_Student0`
    FOREIGN KEY (`student_id`)
    REFERENCES `lms`.`Student` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_AssignedStaff_Staff10`
    FOREIGN KEY (`staff_id`)
    REFERENCES `lms`.`Staff` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `lms`.`Notices`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `lms`.`Notices` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `staff_id` INT NOT NULL,
  `subject` VARCHAR(1024) NOT NULL,
  `body` VARCHAR(4096) NOT NULL,
  `created_at` TIMESTAMP(2) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  INDEX `fk_AssignedStaff_Staff1_idx` (`staff_id` ASC),
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_AssignedStaff_Staff100`
    FOREIGN KEY (`staff_id`)
    REFERENCES `lms`.`Staff` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `lms`.`Viewed`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `lms`.`Viewed` (
  `student_id` INT NOT NULL,
  `content_id` INT NOT NULL,
  `created_at` TIMESTAMP(2) NOT NULL,
  INDEX `fk_ContentViewed_Student1_idx` (`student_id` ASC),
  INDEX `fk_ContentViewed_CourseContent1_idx` (`content_id` ASC),
  CONSTRAINT `fk_ContentViewed_Student1`
    FOREIGN KEY (`student_id`)
    REFERENCES `lms`.`Student` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_ContentViewed_CourseContent1`
    FOREIGN KEY (`content_id`)
    REFERENCES `lms`.`Content` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
