import com.kevinvi.common.UiModel

interface Mapper<T, U : UiModel> {
	fun mapToUi(item: T): U

	fun mapToData(item: U): T
}
