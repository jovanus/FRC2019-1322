/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.calibrations.*;

public class CC_ClawAutoGrab extends Command {
  public CC_ClawAutoGrab() {
    requires(Robot.CLAW);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(!Robot.CLAW.getClaw() && !K_System.KeSYS_b_PracticeBot) {
      Robot.CLAW.controlClaw(false);
    }

    if(!Robot.CLAW.getDisk() && !Robot.m_oi.AuxStick.bButton.get()) {
      Robot.CLAW.diskGrabber(false);
    }

    Robot.CLAW.intakePower(Robot.m_oi.AuxStick.getRightStickY());
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
