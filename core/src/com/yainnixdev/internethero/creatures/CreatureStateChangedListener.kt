package com.yainnixdev.internethero.creatures

import com.yainnixdev.internethero.creatures.appearence.HeroLook

interface CreatureStateChangedListener {
  fun appearanceChanged(tempLook: HeroLook? = null)
}