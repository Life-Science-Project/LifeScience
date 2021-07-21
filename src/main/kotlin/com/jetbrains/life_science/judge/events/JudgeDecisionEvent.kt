package com.jetbrains.life_science.judge.events

import org.springframework.context.ApplicationEvent

abstract class JudgeDecisionEvent(entityId: Long) : ApplicationEvent(entityId)
