/*
 * Copyright 2009-2012 the original author or authors. Licensed under the Apache
 * License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

package eu.cassandra.platform.appliances;

/**
 * This class contains constant definitions used throughout the household models.
 * 
 * @author Antonios Chrysopoulos
 * @version 1.0, Date: 13.12.10
 */
public class AppliancesConstants
{

  // GENERAL VARIABLES -----------------------------//
  public static final int PERCENTAGE = 100;
  public static final int THOUSAND = 1000;
  public static final int KWH = 1000;
  public static final int MWH = 1000000;
  public static final int MILLION = 1000000;
  public static final int MEAN_TARIFF_DURATION = 5;
  public static final double HALF = 0.5;
  public static final double EPSILON = 2.7;

  // TIME VARIABLES -----------------------------//
  public static final int DAYS_OF_COMPETITION = 63;
  public static final int WEEKS_OF_COMPETITION = 9;
  public static final int DAYS_OF_BOOTSTRAP = 14;
  public static final int WEEKS_OF_BOOTSTRAP = 2;
  public static final int DAYS_OF_WEEK = 7;
  public static final int WEEKDAYS = 5;
  public static final int QUARTERS_OF_HOUR = 4;
  public static final int QUARTERS_OF_DAY = 96;
  public static final int HOURS_OF_DAY = 24;
  public static final int MINUTES_OF_DAY = 1440;

  // FRIDGE VARIABLES -----------------------------//
  public static final int FRIDGE_SATURATION = 100;
  public static final int FRIDGE_POWER_MEAN = 106;
  public static final int FRIDGE_POWER_VARIANCE = 18;
  public static final int FRIDGE_DURATION_CYCLE_MEAN = 10;
  public static final int FRIDGE_DURATION_CYCLE_VARIANCE = 2;

  // FREEZER VARIABLES -----------------------------//
  public static final int FREEZER_SATURATION = 100;
  public static final int FREEZER_POWER_MEAN = 106;
  public static final int FREEZER_POWER_VARIANCE = 18;
  public static final int FREEZER_DURATION_CYCLE_MEAN = 10;
  public static final int FREEZER_DURATION_CYCLE_VARIANCE = 2;

  // LIGHTS VARIABLES -----------------------------//
  public static final int LIGHTS_SATURATION = 100;
  public static final int LIGHTS_POWER_MEAN = 350;
  public static final int LIGHTS_POWER_VARIANCE = 58;
  public static final int LIGHTS_DURATION_CYCLE = 1;

  // OTHERS VARIABLES -----------------------------//
  public static final int OTHERS_SATURATION = 100;
  public static final int OTHERS_POWER_MEAN = 500;
  public static final int OTHERS_POWER_VARIANCE = 83;
  public static final int OTHERS_DURATION_CYCLE = 1;

  // DISHWASHER VARIABLES -----------------------------//
  public static final int DISHWASHER_SATURATION = 100;
  public static final int DISHWASHER_POWER_MEAN = 530;
  public static final int DISHWASHER_POWER_VARIANCE = 88;
  public static final int DISHWASHER_DURATION_CYCLE_MEAN = 8;
  public static final int DISHWASHER_DURATION_CYCLE_VARIANCE = 1;

  // DRYER VARIABLES -----------------------------//
  public static final int DRYER_SATURATION = 40;
  public static final int DRYER_POWER_MEAN = 1410;
  public static final int DRYER_POWER_VARIANCE = 235;
  public static final int DRYER_DURATION_CYCLE_MEAN = 7;
  public static final int DRYER_DURATION_CYCLE_VARIANCE = 1;

  public static final int DRYER_SECOND_PHASE = 3;
  public static final int DRYER_THIRD_PHASE = 6;
  public static final int DRYER_THIRD_PHASE_LOAD = 250;

  // WASHING MACHINE VARIABLES -----------------------------//
  public static final int WASHING_MACHINE_SATURATION = 70;
  public static final int WASHING_MACHINE_POWER_MEAN = 100;
  public static final int WASHING_MACHINE_POWER_VARIANCE = 600;
  public static final int WASHING_MACHINE_DURATION_CYCLE_MEAN = 8;
  public static final int WASHING_MACHINE_DURATION_CYCLE_VARIANCE = 8;

  // COFFEEMAKER VARIABLES -----------------------------//
  public static final double COFFEEMAKER_SATURATION = 100;
  public static final int COFFEEMAKER_POWER_MEAN = 850;
  public static final int COFFEEMAKER_POWER_VARIANCE = 8;
  public static final int COFFEEMAKER_POWER_STANDBY_MEAN = 50;
  public static final int COFFEEMAKER_POWER_STANDBY_VARIANCE = 5;
  public static final int COFFEEMAKER_ONDURATION_CYCLE_MEAN = 5;
  public static final int COFFEEMAKER_ONDURATION_CYCLE_VARIANCE = 1;
  public static final int COFFEEMAKER_OFFDURATION_CYCLE_MEAN = 25;
  public static final int COFFEEMAKER_OFFDURATION_CYCLE_VARIANCE = 1;

  // STOVE VARIABLES -----------------------------//
  public static final double STOVE_SATURATION = 80;
  public static final int STOVE_POWER_MEAN = 1840;
  public static final int STOVE_POWER_VARIANCE = 307;
  public static final int STOVE_DURATION_CYCLE_MEAN = 2;
  public static final int STOVE_DURATION_CYCLE_VARIANCE = 2;

  // OVEN VARIABLES -----------------------------//
  public static final double OVEN_SATURATION = 80;
  public static final int OVEN_POWER_MEAN = 1840;
  public static final int OVEN_POWER_VARIANCE = 307;
  public static final int OVEN_POWER_STANDBY_MEAN = 1840;
  public static final int OVEN_POWER_STANDBY_VARIANCE = 307;
  public static final int OVEN_ONDURATION_CYCLE_MEAN = 2;
  public static final int OVEN_ONDURATION_CYCLE_VARIANCE = 2;
  public static final int OVEN_OFFDURATION_CYCLE_MEAN = 2;
  public static final int OVEN_OFFDURATION_CYCLE_VARIANCE = 2;

  // MICROWAVE VARIABLES -----------------------------//
  public static final double MICROWAVE_SATURATION = 50;
  public static final int MICROWAVE_POWER_MEAN = 2000;
  public static final int MICROWAVE_POWER_VARIANCE = 307;
  public static final int MICROWAVE_DURATION_CYCLE_MEAN = 4;
  public static final int MICROWAVE_DURATION_CYCLE_VARIANCE = 2;

  // COMPUTER VARIABLES -----------------------------//
  public static final double COMPUTER_SATURATION = 70;
  public static final int COMPUTER_POWER_MEAN = 400;
  public static final int COMPUTER_POWER_VARIANCE = 30;
  public static final int COMPUTER_POWER_STANDBY_MEAN = 20;
  public static final int COMPUTER_POWER_STANDBY_VARIANCE = 5;

  // TV VARIABLES -----------------------------//
  public static final double TV_SATURATION = 100;
  public static final int TV_POWER_MEAN = 1840;
  public static final int TV_POWER_VARIANCE = 307;

  // GAMING_CONSOLE VARIABLES -----------------------------//
  public static final double GAMING_CONSOLE_SATURATION = 100;
  public static final int GAMING_CONSOLE_POWER_MEAN = 1840;
  public static final int GAMING_CONSOLE_POWER_VARIANCE = 307;

  // STEREO VARIABLES -----------------------------//
  public static final double STEREO_SATURATION = 100;
  public static final int STEREO_POWER_MEAN = 1840;
  public static final int STEREO_POWER_VARIANCE = 307;

  // VACUUM VARIABLES -----------------------------//
  public static final double VACUUM_SATURATION = 100;
  public static final int VACUUM_POWER_MEAN = 1840;
  public static final int VACUUM_POWER_VARIANCE = 307;

}
