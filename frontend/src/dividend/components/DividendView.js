import React from 'react';
import { connect } from 'react-redux';
import { Link } from 'react-router-dom';

import { fetchDividends } from '../actions';

class DividendView extends React.Component {
  componentDidMount() {
    const { id } = this.props.match.params;

    this.props.fetchDividends(id);
  }

  render() {
    if (!this.props.dividend) {
      return <div>Loading...</div>;
    }

    const { instituteName, dividendNo } = this.props.dividend;

    return (
      <div>
        <h1>{instituteName}</h1>
        <h5>{dividendNo}</h5>
        <Link to="/dividends" className="ui button primary">
          Back to List
        </Link>
      </div>
    );
  }
}

const mapStateToProps = (state, ownProps) => {
  return { dividend: state.dividends[ownProps.match.params.id] };
};

export default connect(
  mapStateToProps,
  { fetchDividends }
)(DividendView);
