package edu.nextstep.camp.calculator

import com.google.common.truth.Truth.assertThat
import edu.nextstep.camp.calculator.domain.Operator
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class MainPresenterTest {
    private lateinit var presenter: MainContract.Presenter
    private lateinit var view: MainContract.View

    @Before
    fun setUp() {
        view = mockk()
        presenter = MainPresenter(view)
    }

    @Test
    fun `숫자가 입력되면 수식에 추가되고 변경된 수식을 보여줘야 한다`() {
        // given
        val expressionSlot = slot<String>()
        every { view.showExpression(capture(expressionSlot)) } answers { nothing }

        // when
        presenter.clickOperand(1)

        // then
        val actual = expressionSlot.captured
        assertThat(actual).isEqualTo("1")
        verify { view.showExpression(actual) }
    }

    @Test
    fun `숫자1이_입력된후_숫자2가_입력되면_12가_화면에_출력된다`() {
        // given
        val expressionSlot = slot<String>()
        every { view.showExpression(capture(expressionSlot)) } answers { nothing }
        presenter.clickOperand(1)

        // when
        presenter.clickOperand(2)

        // then
        val actual = expressionSlot.captured
        assertThat(actual).isEqualTo("12")
        verify { view.showExpression(actual) }
    }

    @Test
    fun `숫자12가_입력된_후_수식+가_입력되면_12+가_화면에_출력된다`() {
        // given
        val expressionSlot = slot<String>()
        every { view.showExpression(capture(expressionSlot)) } answers { nothing }
        presenter.clickOperand(1)
        presenter.clickOperand(2)

        // when
        presenter.clickOperator(Operator.Plus)

        // then
        val actual = expressionSlot.captured
        assertThat(actual).isEqualTo("12 +")
        verify { view.showExpression(actual) }
    }

    @Test
    fun `숫자 12가 입력된 후 수식 "-" 가 입력되면 "12-"가 화면에 출력된다`() {
        // given
        val expressionSlot = slot<String>()
        every { view.showExpression(capture(expressionSlot)) } answers { nothing }
        presenter.clickOperand(1)
        presenter.clickOperand(2)

        // when
        presenter.clickOperator(Operator.Minus)

        // then
        val actual = expressionSlot.captured
        assertThat(actual).isEqualTo("12 -")
        verify { view.showExpression(actual) }
    }

    @Test
    fun `수식 12-2가 입력된후 수식 '=' 을 클릭하면 10이 화면에 출력된다`() {
        // given
        val expressionSlot = slot<String>()
        every { view.showExpression(capture(expressionSlot)) } answers { nothing }
        presenter.clickOperand(1)
        presenter.clickOperand(2)
        presenter.clickOperator(Operator.Minus)
        presenter.clickOperand(2)

        // when
        presenter.clickEqual()

        // then
        val actual = expressionSlot.captured
        assertThat(actual).isEqualTo("10")
        verify { view.showExpression(actual) }
    }

    @Test
    fun `입력된 수식이 없을 때 연산자 '+' 을 클릭하면 아무것도 출력되지 않는다`() {
        // given
        val expressionSlot = slot<String>()
        every { view.showExpression(capture(expressionSlot)) } answers { nothing }

        // when
        presenter.clickOperator(Operator.Plus)

        // then
        val actual = expressionSlot.captured
        assertThat(actual).isEqualTo("")
        verify { view.showExpression(actual) }
    }

    @Test
    fun `입력된 수식이 없을 때 지우기 버튼을 클릭하면 아무것도 지워지지 않고 그대로이다`() {
        // given
        val expressionSlot = slot<String>()
        every { view.showExpression(capture(expressionSlot)) } answers { nothing }

        // when
        presenter.clickDelete()

        // then
        val actual = expressionSlot.captured
        assertThat(actual).isEqualTo("")
        verify { view.showExpression(actual) }
    }

    @Test
    fun `12가 입력됬을 때 지우기 버튼을 한번 클릭하면 1이 출력된다`() {
        // given
        val expressionSlot = slot<String>()
        every { view.showExpression(capture(expressionSlot)) } answers { nothing }
        presenter.clickOperand(1)
        presenter.clickOperand(2)

        // when
        presenter.clickDelete()

        // then
        val actual = expressionSlot.captured
        assertThat(actual).isEqualTo("1")
        verify { view.showExpression(actual) }
    }
}