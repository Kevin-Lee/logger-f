package loggerf.scalaz

import scalaz._
import Scalaz._
import scalaz.effect._
import effectie.scalaz.Effectful._
import effectie.scalaz.EffectConstructor
import hedgehog._
import hedgehog.runner._
import loggerf.logger.LoggerForTesting

/**
 * @author Kevin Lee
 * @since 2020-04-13
 */
object LoggerEitherSpec extends Properties {
  override def tests: List[Test] = List(
    property("""test LoggerEither.debugEither(F[A \/ B])""", testLoggerEitherDebugEitherFEAB)
  , property("""test LoggerEither.infoEither(F[A \/ B])""", testLoggerEitherInfoEitherFEAB)
  , property("""test LoggerEither.warnEither(F[A \/ B])""", testLoggerEitherWarnEitherFEAB)
  , property("""test LoggerEither.errorEither(F[A \/ B])""", testLoggerEitherErrorEitherFEAB)
  )


  def testLoggerEitherDebugEitherFEAB: Property = for {
    rightInt <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("rightInt")
    leftString <- Gen.string(Gen.unicode, Range.linear(1, 20)).log("leftString")
    isRight <- Gen.boolean.log("isRight")
  } yield {

    implicit val logger: LoggerForTesting = LoggerForTesting()

    def runLog[F[_] : EffectConstructor : Monad](eab: String \/ Int): F[String \/ Int] =
      LoggerEither[F].debugEither(effectOf(eab))(a => s"Error: $a", b => b.toString)

    val eab = if (isRight) rightInt.right[String] else leftString.left[Int]

    val result = runLog[IO](eab).unsafePerformIO()

    val expected = eab match {
      case \/-(n) =>
        LoggerForTesting(
          debugMessages = Vector(n.toString)
        , infoMessages = Vector.empty
        , warnMessages = Vector.empty
        , errorMessages = Vector.empty
        )

      case -\/(msg) =>
        LoggerForTesting(
          debugMessages = Vector(s"Error: $msg")
        , infoMessages = Vector.empty
        , warnMessages = Vector.empty
        , errorMessages = Vector.empty
        )
    }

    Result.all(
      List(
        (result ==== eab).log("result ==== eab failed")
      , (logger ==== expected).log("logger ==== expected failed")
      )
    )
  }

  def testLoggerEitherInfoEitherFEAB: Property = for {
    rightInt <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("rightInt")
    leftString <- Gen.string(Gen.unicode, Range.linear(1, 20)).log("leftString")
    isRight <- Gen.boolean.log("isRight")
  } yield {

    implicit val logger: LoggerForTesting = LoggerForTesting()

    def runLog[F[_] : EffectConstructor : Monad](eab: String \/ Int): F[String \/ Int] =
     LoggerEither[F].infoEither(effectOf(eab))(a => s"Error: $a", b => b.toString)

    val eab = if (isRight) rightInt.right[String] else leftString.left[Int]

    val result = runLog[IO](eab).unsafePerformIO()

    val expected = eab match {
      case \/-(n) =>
        LoggerForTesting(
          debugMessages = Vector.empty
        , infoMessages = Vector(n.toString)
        , warnMessages = Vector.empty
        , errorMessages = Vector.empty
        )

      case -\/(msg) =>
        LoggerForTesting(
          debugMessages = Vector.empty
        , infoMessages = Vector(s"Error: $msg")
        , warnMessages = Vector.empty
        , errorMessages = Vector.empty
        )
    }

    Result.all(
      List(
        (result ==== eab).log("result ==== eab failed")
      , (logger ==== expected).log("logger ==== expected failed")
      )
    )
  }

  def testLoggerEitherWarnEitherFEAB: Property = for {
    rightInt <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("rightInt")
    leftString <- Gen.string(Gen.unicode, Range.linear(1, 20)).log("leftString")
    isRight <- Gen.boolean.log("isRight")
  } yield {

    implicit val logger: LoggerForTesting = LoggerForTesting()

    def runLog[F[_] : EffectConstructor : Monad](eab: String \/ Int): F[String \/ Int] =
     LoggerEither[F].warnEither(effectOf(eab))(a => s"Error: $a", b => b.toString)

    val eab = if (isRight) rightInt.right[String] else leftString.left[Int]

    val result = runLog[IO](eab).unsafePerformIO()

    val expected = eab match {
      case \/-(n) =>
        LoggerForTesting(
          debugMessages = Vector.empty
        , infoMessages = Vector.empty
        , warnMessages = Vector(n.toString)
        , errorMessages = Vector.empty
        )

      case -\/(msg) =>
        LoggerForTesting(
          debugMessages = Vector.empty
        , infoMessages = Vector.empty
        , warnMessages = Vector(s"Error: $msg")
        , errorMessages = Vector.empty
        )
    }

    Result.all(
      List(
        (result ==== eab).log("result ==== eab failed")
      , (logger ==== expected).log("logger ==== expected failed")
      )
    )
  }

  def testLoggerEitherErrorEitherFEAB: Property = for {
    rightInt <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("rightInt")
    leftString <- Gen.string(Gen.unicode, Range.linear(1, 20)).log("leftString")
    isRight <- Gen.boolean.log("isRight")
  } yield {

    implicit val logger: LoggerForTesting = LoggerForTesting()

    def runLog[F[_] : EffectConstructor : Monad](eab: String \/ Int): F[String \/ Int] =
     LoggerEither[F].errorEither(effectOf(eab))(a => s"Error: $a", b => b.toString)

    val eab = if (isRight) rightInt.right[String] else leftString.left[Int]

    val result = runLog[IO](eab).unsafePerformIO()

    val expected = eab match {
      case \/-(n) =>
        LoggerForTesting(
          debugMessages = Vector.empty
        , infoMessages = Vector.empty
        , warnMessages = Vector.empty
        , errorMessages = Vector(n.toString)
        )

      case -\/(msg) =>
        LoggerForTesting(
          debugMessages = Vector.empty
        , infoMessages = Vector.empty
        , warnMessages = Vector.empty
        , errorMessages = Vector(s"Error: $msg")
        )
    }

    Result.all(
      List(
        (result ==== eab).log("result ==== eab failed")
      , (logger ==== expected).log("logger ==== expected failed")
      )
    )
  }

}
