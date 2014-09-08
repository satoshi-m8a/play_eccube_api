package models

object ComponentRegistry extends
ProductServiceComponent with
ProductRepositoryComponent with
CategoryServiceComponent with
CategoryRepositoryComponent {

  val productRepository = new ProductRepository
  val Products = new ProductService

  val categoryRepository = new CategoryRepository
  val Categories = new CategoryService
}
