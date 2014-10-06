import org.specs2.execute.AsResult
import org.specs2.mutable.{Around, Specification}

import play.api.test.Helpers._

trait TestSupport extends TestHelpers {
  self: Specification =>

  trait WithApplication extends Around {
    def around[T: AsResult](t: => T) = running(fakeApplication) {
      AsResult(t)
    }
  }

}