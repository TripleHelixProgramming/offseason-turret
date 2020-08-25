/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.turret.commands;

import edu.wpi.first.wpilibj.command.Command;

import static frc.robot.turret.Turret.getTurret;
import static frc.robot.limelight.Limelight.getLimelight;

public class xTargeting extends Command {

  private double xError, xLastError, xDerivative, xOutput = 0;

  private double xKP = 0.0;
  private double xKD = 0.0;

  public xTargeting() {
    requires(getTurret());
  }

  protected void initialize() {
  }

  protected void execute() {
    xError = -getLimelight().getXOffset();
    xDerivative = xError - xLastError;
    xOutput = xError * xKP + xDerivative * xKD;
    getTurret().setXRPM(xOutput);
    xLastError = xError;
  }

  protected boolean isFinished() {
    if (Math.abs(getLimelight().getXOffset()) < 1) return true;
    return false;
  }

  protected void end() {
    getTurret().setXRPM(0);
  }

  protected void interrupted() {
    end();
  }
}
