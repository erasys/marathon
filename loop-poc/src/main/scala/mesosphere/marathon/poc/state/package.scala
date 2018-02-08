package mesosphere.marathon
package poc

import java.util.UUID

package state {
  /**
    * Represents some command to run
    */
  case class RunSpec(
      id: String,
      variant: Variant,
      version: String,
      command: String) {
    def ref = RunSpecRef(id, variant)
  }

  case class RunSpecRef(
      id: String,
      variant: Variant) {
    override def toString(): String = s"${id}#${variant}"
  }

  case class Instance(
      instanceId: UUID,
      runSpec: RunSpecRef)

  case class DeploymentPolicy(
      id: UUID,
      runSpec: RunSpecRef,
      placement: String,
      recovery: String,
      schedule: String)

  sealed trait UpgradePolicy
  case class RollingUpdate(
      overScale: Int,
      maxUnready: Int,
      instancesPerSecond: Option[Int]) extends UpgradePolicy

  case class Deployment(
      deploymentPolicyId: UUID,
      upgradePolicy: UpgradePolicy,
      targetInstanceCount: Int,
      runSpec: RunSpecRef)

}

package object state {
  type Path = String
  type Variant = String
}
