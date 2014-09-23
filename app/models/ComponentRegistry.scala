package models

object ComponentRegistry extends
ProductServiceComponent with
ProductRepositoryComponent with
CategoryServiceComponent with
CategoryRepositoryComponent with
PageServiceComponent with
PageRepositoryComponent with
MemberServiceComponent with
MemberRepositoryComponent {

  val productRepository = new ProductRepository
  val Products = new ProductService

  val categoryRepository = new CategoryRepository
  val Categories = new CategoryService

  val pageRepository = new PageRepository
  val Pages = new PageService

  val memberRepository = new MemberRepository
  val Members = new MemberService
}
