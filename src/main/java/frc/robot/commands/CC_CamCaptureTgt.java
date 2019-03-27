/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.calibrations.K_System;

public class CC_CamCaptureTgt extends Command {
  public CC_CamCaptureTgt() {
     requires(Robot.VISION);
  }
  
  boolean LeVSN_b_ImgCaptureVld;


  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    LeVSN_b_ImgCaptureVld = false;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    LeVSN_b_ImgCaptureVld = Robot.VISION.captureVSN_CamImgData();
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return LeVSN_b_ImgCaptureVld;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.VISION.MngVSN_CamImgProc();
    if (K_System.KCMD_b_DebugEnbl == true) {
      Robot.DASHBOARD.updateSmartDashCamData();
    }
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
