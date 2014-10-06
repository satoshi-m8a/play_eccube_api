import play.api.test.FakeApplication
import org.specs2.mutable._
import play.api.test.Helpers._

trait TestHelpers {
  self: Specification =>

  def fakeApplication = FakeApplication(additionalConfiguration = inMemoryDatabase() + (
    "applyEvolutions.default" -> "true"
    ))

}