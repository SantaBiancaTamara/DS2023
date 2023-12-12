const Login = () => {
    const  login = async () => {
        const response = await fetch("/api1", {
            credentials: 'include',
            method: 'POST',
            headers: {'Content-Type': 'text/plain'}
        });
        // const movies = await response.json();
        console.log(response);
    }
  return (
      <div className="Login-form-container">
        <form className="Login-form" onSubmit={(event) => {
            event.preventDefault()
            login();
        }}>
          <div className="Login-form-content">
            <h3 className="Login-form-title">Sign In</h3>
            <div className="form-group mt-3">
              <label>Email address</label>
              <input
                  type="email"
                  className="form-control mt-1"
                  placeholder="Enter email"
              />
            </div>
            <div className="form-group mt-3">
              <label>Password</label>
              <input
                  type="password"
                  className="form-control mt-1"
                  placeholder="Enter password"
              />
            </div>
            <div className="d-grid gap-2 mt-3">
              <button type="submit"  className="btn btn-primary">
                Submit
              </button>
            </div>
          </div>
        </form>
      </div>
  )
}
export default Login