import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.travelapp.data.model.LoginRequest
import com.example.travelapp.domain.model.User
import com.example.travelapp.domain.model.UserState
import com.example.travelapp.domain.usecase.UserUseCase
import com.example.travelapp.presentation.login.LoginViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertIs

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var userUseCase: UserUseCase

    private lateinit var viewModel: LoginViewModel

    @Before
    fun setup() {
        viewModel = LoginViewModel(userUseCase)
    }

    @Test
    fun loginSuccessUpdatesStateToSuccess() = runTest {

        val loginRequest = LoginRequest(email = "phincon@academy.com", password = "password")
        val user = User(
            email = "test@example.com",
            firstName = "John",
            lastName = "Doe",
            avatar = "avatar_url",
            phone = "1234567890",
            token = "token123"
        )
        `when`(userUseCase.login(loginRequest)).thenReturn(user)


        viewModel.login(loginRequest)

        val result = viewModel.userState.first()
        assertIs<UserState.Success>(result)
        assertEquals(user, result.userData)
    }

    @Test
    fun loginFailureUpdatesStateToError() = runTest {

        val loginRequest = LoginRequest(email = "phincon@academy.com", password = "wrong_password")
        val errorMessage = "Login failed"
        `when`(userUseCase.login(loginRequest)).thenThrow(RuntimeException(errorMessage))


        viewModel.login(loginRequest)


        val result = viewModel.userState.first()
        assertIs<UserState.Error>(result)
        assertEquals(errorMessage, result.message)
    }
}
