/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class CC_AutoTgtDrvEnbl extends Command {
  public CC_AutoTgtDrvEnbl() {
  }
  
  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.NAV.setNAV_CL_DrvRqstActv(true);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    /* Nothing Required */
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return(true);
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    System.out.println("***  AutoTgtDrvEnbl Command. ***");
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
