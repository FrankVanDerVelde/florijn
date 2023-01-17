import InMemoryUserRepo from "../../mockRepos/InMemoryUserRepo";

// instances for testing
let userRepository;

describe('UserRepository', () => {


beforeEach(function () {
    // an authorsService to be provided to the booksView component
    userRepository = new InMemoryUserRepo();
});

it('constructs a proper user repository', function () {
    expect(userRepository.entities.length).toBeGreaterThan(0);
})

it('findAll returns all', function () {
    let users = userRepository.findAll();
    expect(users).toStrictEqual(userRepository.entities);
})

it('findById returns the specified user', function () {
    for (let i = 1; i < 20; i++) {
        const user = userRepository.entities[i];
        expect(userRepository.getUserById(user.id)).toEqual(user);
    }
})

})

