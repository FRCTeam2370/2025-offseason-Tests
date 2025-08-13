// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Commands.RunManipulator;
import frc.robot.Subsystems.ManipulatorSubsystem;

public class RobotContainer {

  public static CommandXboxController driver = new CommandXboxController(0);

  private static ManipulatorSubsystem manipulatorSubsystem = new ManipulatorSubsystem();

  public RobotContainer() {
    configureBindings();
  }

  private void configureBindings() {
    driver.rightBumper().whileTrue(new RunManipulator(manipulatorSubsystem, 0.75));//<--- this is a great speed!!!!
    driver.leftBumper().whileTrue(new RunManipulator(manipulatorSubsystem, -0.5));
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
