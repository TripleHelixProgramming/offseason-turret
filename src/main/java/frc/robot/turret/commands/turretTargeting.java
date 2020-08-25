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

public class turretTargeting extends Command {

  private double xError, xLastError, xDerivative, xOutput, yError, yLastError, yDerivative, yOutput = 0;

  private double xKP = 0.0;
  private double xKD = 0.0;

  private double yKP = 0.0;
  private double yKD = 0.0;

  public turretTargeting() {
    requires(getTurret());
  }

  protected void initialize() {
  }

  protected void execute() {
    xError = -getLimelight().getXOffset();
    yError = -getLimelight().getYOffset();

    xDerivative = xError - xLastError;
    yDerivative = yError - yLastError;

    xOutput = xError * xKP + xDerivative * xKD;
    yOutput = yError * yKP + yDerivative * yKD;

    getTurret().setXRPM(xOutput);
    getTurret().setYRPM(yOutput);

    xLastError = xError;
    yLastError = yError;
  }

  protected boolean isFinished() {
    if (Math.abs(getLimelight().getXOffset()) < 1) return true;
    return false;
  }

  protected void end() {
    getTurret().setXRPM(0);
    getTurret().setYRPM(0);
  }

  protected void interrupted() {
    end();
  }
}
